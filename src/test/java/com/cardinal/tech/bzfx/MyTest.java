package com.cardinal.tech.bzfx;

import com.alibaba.druid.filter.config.ConfigTools;
import com.cardinal.tech.bzfx.enums.biz.RoleEnum;
import com.cardinal.tech.bzfx.util.UserPasswordEncoder;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MyTest {
    @Test
    public void testDruidPasswordEncrypt() throws Exception {
        ConfigTools.main(new String[]{"Bzfx@2021"});
//        ConfigTools.main(new String[]{"yy@2020AL"});
    }

    @Test
    public void testEncryptPassword() {
//        var encode = new UserPasswordEncoder().encode("Guest2020@bzms");
        var encode = new UserPasswordEncoder().encode("Admin2021@bzfx");
        System.out.println(encode);
    }

    @Test
    public void testRoleEnum(){
        final var roles = RoleEnum.getRolesById(12345);
        roles.forEach(r-> System.out.println(r.code()));
    }
}