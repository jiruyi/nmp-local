package com.matrictime.network.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.model.NmpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JwtUtils {
    private static  final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * @title createToken
     * @param [networkUser]
     * @return java.lang.String
     * @description  生成jwt
     * @author jiruyi
     * @create 2021/9/6 0006 19:04
     */
    public static String createToken(NmpUser networkUser){
        return  JWT.create().withAudience(String.valueOf(networkUser.getUserId()))
                .withIssuedAt(new Date())
                .withClaim("loginAccount",networkUser.getLoginAccount())
                .withClaim("userId",String.valueOf(networkUser.getUserId()))
                .sign(Algorithm.HMAC256(String.valueOf(networkUser.getUserId())));

    }

    /**
     * @title verifyToken
     * @param [token, secret]
     * @return void
     * @description 检验合法性，其中secret参数就应该传入的是用户的id
     * @author jiruyi
     * @create 2021/9/6 0006 19:03
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
     * @title getClaimByName
     * @param [token, name]
     * @return com.auth0.jwt.interfaces.Claim
     * @description  解析jwt
     * @author jiruyi
     * @create 2021/9/6 0006 19:08
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}
