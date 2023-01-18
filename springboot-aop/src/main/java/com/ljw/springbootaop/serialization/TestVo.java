package com.ljw.springbootaop.serialization;

/**
 * @Description: todo
 * @Author: jianweil
 * @date: 2022/11/18 13:05
 */
public class TestVo {
    private Long id;
    private String name;

  /*  public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public TestVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
