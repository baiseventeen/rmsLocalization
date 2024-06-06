package cn.gst.filter;

import cn.gst.util.JwtUtil;
import cn.gst.vo.ResultBean;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;


/**
 * 全局登录验证过滤器
 * @author lzx
 */
@Component
public class LoginGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //判断是否登录请求
        ServerHttpRequest request = exchange.getRequest();
        if(request.getURI().getPath().contains("login")){
            //为登录请求，无须进一步验证，放行
            return chain.filter(exchange);
        }

        //非登录请求，获取token
        String token = request.getHeaders().getFirst("Authorization");
        //未携带token
        if(StringUtils.isBlank(token)){
            return fail(exchange);
        }

        //携带token，进行验证
        try {
            //TODO:开发完毕后，删除开发登录验证后门
            if(token.equals("test")){
                return chain.filter(exchange);
            }
            Claims claims = JwtUtil.parseJWT(token);
        } catch (Exception jwtException) {
            jwtException.printStackTrace();
            //解析出错，令牌过期或不合法，
            return fail(exchange);
        }

        //jwt验证通过，放行
        return chain.filter(exchange);
    }

    //过滤优先级设置
    @Override
    public int getOrder() {
        return 0;
    }


    //验证失败,返回信息
    private Mono<Void> fail(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        ResultBean data = ResultBean.error(401,"未登录");

        DataBuffer buffer = null;
        try {
            byte[] bytes = JSON.toJSONString(data).getBytes("utf-8");
            buffer = response.bufferFactory().wrap(bytes);

            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type","application/json;charset=UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //返回未登录 不放行
        return response.writeWith(Mono.just(buffer));
    }


}
