package com.qinyi.renhai.util;

import java.util.*;
import java.util.function.Function;

public class SequenceOperator<T> {
    private List<T> list;

    private SequenceOperator() {
    }

    public static <T> SequenceOperator<T> build(List<T> list) {
        SequenceOperator<T> u = new SequenceOperator<T>();
        u.list = list;
        return u;
    }

    /**
     * @Description: 第一个元素, 可能为空
     * @Author: suyikun
     * @Date: 2022/9/14 18:05
     **/
    public T first() {
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @Description: 最后一个元素, 可能为空
     * @Author: suyikun
     * @Date: 2022/9/14 18:04
     **/
    public T last() {
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * @Description: 获取前几个元素
     * @Author: suyikun
     * @Date: 2022/9/14 18:04
     **/
    public SequenceOperator<T> prefix(int count) {
        if (null == list || list.isEmpty()) {
            return this;
        }
        if (count > this.list.size()) {
            return this;
        }
        this.list = this.list.subList(0, count);
        return this;
    }

    /**
     * @Description: 获取最后几个元素
     * @Author: suyikun
     * @Date: 2022/9/14 18:04
     **/
    public SequenceOperator<T> suffix(int count) {
        if (null == list || list.isEmpty()) {
            return this;
        }
        if (count > this.list.size()) {
            return this;
        }
        this.list = this.list.subList(this.list.size() - count, this.list.size());
        return this;
    }

    /**
     * @Description: 是否包含
     * @Author: suyikun
     * @Date: 2022/9/14 18:05
     **/
    public boolean contain(Function<T, Boolean> func) {
        if (null == list || list.isEmpty()) {
            return false;
        }
        for (T t : list) {
            Boolean isContain = func.apply(t);
            if (isContain) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: 获取第一个符合条件的元素
     * @Author: suyikun
     * @Date: 2022/9/14 18:05
     **/
    public T firstWhere(Function<T, Boolean> func) {
        if (null == list || list.isEmpty()) {
            return null;
        }
        for (T t : list) {
            Boolean res = func.apply(t);
            if (res) {
                return t;
            }
        }
        return null;
    }

    /**
     * @Description: 排序  多用lambda获取属性.sort((Integer::compareTo))
     * @Author: suyikun
     * @Date: 2022/9/14 18:05
     **/
    public SequenceOperator<T> sort(Comparator<? super T> c) {
        this.list.sort(c);
        return this;
    }

    /**
     * @Description: 数组转字符串  最好是基础类型 分割符默认为 ","
     * @Author: suyikun
     * @Date: 2022/9/15 10:51
     **/
    public String join(String separator) {
        if (list == null) {
            return null;
        } else {
            if (separator == null) {
                separator = ",";
            }
            StringBuilder buf = new StringBuilder(list.size() * 16);

            for (int i = 0; i < list.size(); ++i) {
                if (i > 0) {
                    buf.append(separator);
                }

                if (list.get(i) != null) {
                    buf.append(list.get(i));
                }
            }
            return buf.toString();
        }
    }

    /**
     * @Description: 过滤数组
     * @Author: suyikun
     * @Date: 2022/9/14 17:53
     **/
    public SequenceOperator<T> filter(Function<T, Boolean> func) {
        if (null == list || list.isEmpty()) {
            return SequenceOperator.build(new ArrayList<>());
        }
        List<T> temp = new ArrayList<T>();
        for (T t : list) {
            Boolean r = func.apply(t);
            if (r) {
                temp.add(t);
            }
        }
        this.list = temp;
        return this;
    }


    /**
     * @Description: map到另外一个数组 元素不能为空
     * @Author: suyikun
     * @Date: 2022/9/14 17:53
     **/
    public <R> SequenceOperator<R> map(Function<T, R> func) {
        if (null == list || list.isEmpty()) {
            return SequenceOperator.build(new ArrayList<>());
        }
        List<R> temp = new ArrayList<R>();
        for (T t : list) {
                R r = func.apply(t);
                temp.add(r);
        }
        return SequenceOperator.build(temp);
    }

    /**
     * @Description: compactMap到另外一个数组, 剔除转换失败的
     * @Author: suyikun
     * @Date: 2022/9/14 17:52
     **/
    public <R> SequenceOperator<R> compactMap(Function<T, R> func) {
        if (null == list || list.isEmpty()) {
            return SequenceOperator.build(new ArrayList<>());
        }
        List<R> temp = new ArrayList<R>();
        for (T t : list) {
            try {
                R r = func.apply(t);
                if (null != r) {
                    temp.add(r);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SequenceOperator.build(temp);
    }

    /**
     * @Description: 输出元素
     * @Author: suyikun
     * @Date: 2022/9/14 18:06
     **/
    public List<T> collect() {
        return this.list;
    }

    public Set<T> set() {
        return new HashSet<>(this.list);
    }
}
