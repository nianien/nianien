package com.nianien.test.functions;

import com.nianien.core.functions.Params;
import com.nianien.core.util.StringUtils;
import com.nianien.test.bean.Contact;
import com.nianien.test.bean.Family;
import com.nianien.test.bean.People;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.nianien.core.functions.F.$;

/**
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class TestFunction {


    public static void main(String[] args) {

        Family family = new Family();

        $(family).$(Family::getHost).$(People::getContact).$(Contact::getAddress).$();

        People people = $(new People())
                .$$(People::setId, 101L)
                .$$(People::setName, "name")
                .$$(People::setSex, "male")
                .$$(People::setBirthday, new Date())
                .$$(People::setContact,
                        $(new Contact())
                                .$$(Contact::setAddress, "address*****")
                                .$$(Contact::setTelephone, "137****")
                                .$$(Contact::setEmail, "email****")
                                .$()
                ).$();

        family.setHost(people);

        System.out.println($(family)
                .$$(Family::setAddress, "a")
                .$$(Family::getMembers)
                .$$(System.out::println)
                .$());
        $(family)
                .consumer(e -> System.out.println(e))
                .accept()
                .accept();

        String a = $("a").function(e -> e + ";" + e)
                .apply()
                .apply()
                .$();
        System.out.println(a);

        String str =
                $("")
                        .<String>function2((s1, s2) -> s1 += s2)
                        .apply("a")
                        .apply("c")
                        .<Integer>function2((s1, s2) -> s1 = s1 + s2)
                        .apply(1)
                        .apply(2)
                        .$();
        System.out.println(str);
        String stringBuilder =
                $(new StringBuilder())
                        .<String>consumer2((s, v) -> {
                            if (v != null) s.append(v);
                        })
                        .accept("a")
                        .accept("c")
                        .<Integer>consumer2((s, v) -> {
                            if (v % 2 != 0) s.append(v);
                        })
                        .accept(1)
                        .accept(2)
                        .$().toString();

        System.out.println(stringBuilder);


        Map<String, String> map1 = new HashMap<>();
        map1.put("a", "Alex");
        map1.put("b", "Brown");
        map1.put("c", "Charles");
        map1.put("d", "Darwin");

        Map<String, String> map2 = $(new HashMap())
                .<String, String>consumer3(Map::put)
                .accept("a", "Alex")
                .accept("b", "Brown")
                .accept("c", "Charles")
                .accept("d", "Darwin")
                .$();

        System.out.println(map2);


        System.out.println(build1(1L, "test", ""));
        System.out.println(build2(1L, "test", ""));
    }


    static String build1(long id, String name, String email) {
        StringBuilder builder = new StringBuilder();
        if (id > 0) {
            builder.append(id);
        }
        if (StringUtils.isNotEmpty(name)) {
            builder.append(name);
        }
        if (StringUtils.isNotEmpty(email)) {
            builder.append(email);
        }
        return builder.toString();
    }

    static String build2(long id, String name, String email) {
        return $(new StringBuilder())
                .consumer2(StringBuilder::append)
                .accept(Params.lt(id, 0))
                .accept(Params.isNotEmpty(name))
                .accept(Params.isNotEmpty(email))

                .$().toString();
    }

}
