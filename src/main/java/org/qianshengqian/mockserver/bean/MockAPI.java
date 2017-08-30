package org.qianshengqian.mockserver.bean;

/**
 * Created by Administrator on 2017/8/28.
 */
public class MockAPI<T> {
    private Integer id;
    private Integer parentId;
    private String name;
    private T subject;

    public MockAPI() {
    }

    public MockAPI(Integer id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public MockAPI(Integer id, Integer parentId, String name, T subject) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getSubject() {
        return subject;
    }

    public void setSubject(T subject) {
        this.subject = subject;
    }

    public static void main(String[] args) {
        String str = "1231212\n";
        System.out.println(str);
        int index = str.lastIndexOf("\n");
        System.out.println(index);
        System.out.println(str.length());

        str = str.substring(0, str.length()-1);
        str +="@";

        //str = str.replaceAll("\\n","");
        str = str.trim();
        System.out.println(str);
    }
}
