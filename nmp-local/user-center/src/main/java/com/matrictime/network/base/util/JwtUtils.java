package com.matrictime.network.base.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.matrictime.network.api.modelVo.UserVo;
import com.matrictime.network.exception.ErrorMessageContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 18:04
 * @desc
 */
public class JwtUtils {

    private static  final  Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成jwt
     * @param user
     * @return
     */
    public static String createToken(UserVo user,String destination){
        return  JWT.create().withAudience(String.valueOf(user.getUserId()))
                .withIssuedAt(new Date())
                .withClaim("nickName",user.getNickName())
                .withClaim("loginAccount",user.getLoginAccount())
                .withClaim("userId",String.valueOf(user.getUserId()))
                .withClaim("phone",user.getPhoneNumber())
                .sign(Algorithm.HMAC256(user.getUserId()+KEY_SPLIT_UNDERLINE+destination));

    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的id
     * @param token
     * @param secret
     */
    public static void verifyToken(String token, String secret){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            logger.error(ErrorMessageContants.JWT_VERIFY_FAIL_MSG);
        }
    }


    /**
     * 解析jwt
     * @param token
     * @param name
     * @return
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }

}
