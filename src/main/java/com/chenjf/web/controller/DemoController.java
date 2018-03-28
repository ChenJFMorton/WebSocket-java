package com.chenjf.web.controller;

import com.chenjf.common.AES;
import com.chenjf.domain.User;
import com.chenjf.web.websocket.WSServer;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjf on 2017-09-10.
 */
@RestController
@Scope("prototype")
public class DemoController {

    @RequestMapping(value="/websocket/test", method= RequestMethod.GET)
    public void websocketTest() {
        WSServer.sendAll("aaa");

        System.out.println(11);
    }

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public void test() {
        System.out.println(11);
    }

    @RequestMapping(value="/test/json", method= RequestMethod.POST)
    public void testJson(@RequestBody User user) {
        System.out.println(11);
    }

    public static void main(String[] args) {
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
                "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
                "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
                "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
                "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
                "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
                "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
                "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
                "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
                "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
                "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
                "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
                "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
                "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
                "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
                "20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
                "Db/XcxxmK01EpqOyuxINew==";
        String session_key = "tiihtNczf5v6AKRyjwEUhQ==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        Map map = new HashMap();
        try {
            byte[] resultByte  = AES.decrypt(Base64.decodeBase64(encryptedData),
                    Base64.decodeBase64(session_key),
                    Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String userInfo = new String(resultByte, "UTF-8");
                map.put("status", "1");
                map.put("msg", "解密成功");
                map.put("userInfo", userInfo);
            }else{
                map.put("status", "0");
                map.put("msg", "解密失败");
            }
        }catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String decodeJSON = gson.toJson(map);
        System.out.println(decodeJSON);
    }
}
