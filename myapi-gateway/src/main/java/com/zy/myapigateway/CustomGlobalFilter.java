package com.zy.myapigateway;
import com.zy.myapigateway.utils.IPUtils;
import com.zy.myapigateway.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {


    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 记录日志
        ServerHttpRequest request = exchange.getRequest();
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求唯一标识:" + request.getId());
        log.info("请求路径:" + request.getPath().value());
        log.info("请求方法:" + request.getMethod().toString());
        log.info("请求参数:" + request.getQueryParams());
        //todo 这里本机使用 localhost 会被解析为 IPv6 有一个小bug 测试的时候使用 127.0.0.1 测试就无事发生
        log.info("请求来源地址:" + sourceAddress);

        ServerHttpResponse response = exchange.getResponse();
        //2. 访问控制 黑白名单
        if(!IP_WHITE_LIST.contains(sourceAddress)){

            // 直接完成响应
            return handleNoAuth(response);
        }
        //3. API 网关 进行 用户鉴权（AK SK 是否合法）
        //3.用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        try {

            String body = URLDecoder.decode(headers.getFirst("body"),"utf-8");
            // todo 数据库查询 accessKey 是否已分配给用户,并得到 secretKey
            if(!"zzyy".equals(accessKey)){
                return handleNoAuth(response);
            }
            // todo 随机数校验
            if(Long.parseLong(nonce) > 10000){
                return handleNoAuth(response);
            }
            // 时间校验 例如不超过5分钟
            final Long FIVE_MINUTES = 60 * 5L;
            if(System.currentTimeMillis()/1000 - Long.parseLong(timestamp) > FIVE_MINUTES){
                return handleNoAuth(response);
            }
            // todo 实际情况中 secretKey 从数据库中查出
            String serverSign = SignUtils.getSign(body, "zzzyy");
            if(!serverSign.equals(sign)){
                return handleNoAuth(response);
            }
        } catch (UnsupportedEncodingException e) {
            handleInvokeError(response);
        }

        //4. 请求的模拟接口是否存在 （校验需要看哪个可以少查一次库，放在前面即可）
        //todo 从数据库中查询模拟接口是否存在，以及请求方法是否匹配（还可以校验请求参数）

        //todo 5. 流量染色，并将请求转发到模拟接口
//        Mono<Void> filter = chain.filter(exchange);
        //6. 响应日志
        return handleResponse(exchange,chain);
//        //todo 7. 调用成功，接口调用次数 + 1
//        //8. 调用失败，返回一个规范的错误码
//        if(response.getStatusCode() != HttpStatus.OK){
//            return handleInvokeError(response);
//        }
//        log.info("custom global filter");
//        return filter;
    }



    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 无权限返回
     * @param response
     * @return
     */
    // todo 可以编写全局异常处理逻辑
    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                //装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //等调用完转发的接口后才会执行 debug一下加深理解
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里写数据
                            //破解字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //7.TODO 调用成功后，次数+ 1,其实就是修改数据库
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        sb2.append("<--- {} {} \n");
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        //rspArgs.add(requestUrl);
                                        String data = new String(content, StandardCharsets.UTF_8);//data
                                        sb2.append(data);
                                        //6.打印日志(最后一步)
                                        log.info("响应结果" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            //8.TODO 调用失败后，返回规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置response对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应错误" + e);
            return chain.filter(exchange);
        }
    }
}

