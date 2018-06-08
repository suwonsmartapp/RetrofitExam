package com.example.retrofitexam;

import java.util.List;

public class ForecastResult {
    private List<Forecast> list;

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ForecastResult{");
        sb.append("list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
