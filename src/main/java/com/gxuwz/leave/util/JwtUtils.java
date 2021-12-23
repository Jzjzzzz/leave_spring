package com.gxuwz.leave.util;

import com.gxuwz.leave.entity.vo.UserInfoVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author Jzj
 * @Date 2021/12/22 1:20
 * @Version 1.0
 */
public class JwtUtils {
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String getJwtToken(Long id, String name,int type){

        String JwtToken = Jwts.builder()
                //头部信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //时间设置
                .setSubject("leave-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //主体信息
                .claim("id", id)
                .claim("name", name)
                .claim("type",type)
                .claim("avatar","https://fxb-jzj.oss-cn-guangzhou.aliyuncs.com/Avatar/2021/12/09/678ba02a-6d53-4416-9ea1-df653d2fa447.png")
                //签名哈希
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static Integer getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (Integer)claims.get("id");
    }

    /**
     * 根据token返回用户数据
     * @param request
     * @return
     */
    public static UserInfoVO getUserJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(Long.valueOf(claims.get("id").toString()));
        userInfoVO.setName(claims.get("name").toString());
        userInfoVO.setAvatar(claims.get("avatar").toString());
        userInfoVO.setType((int)claims.get("type"));
        return userInfoVO;
    }
}
