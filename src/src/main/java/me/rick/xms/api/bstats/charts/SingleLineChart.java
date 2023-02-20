//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.rick.xms.api.bstats.charts;

import java.util.concurrent.Callable;
import me.rick.xms.api.bstats.json.JsonObjectBuilder;

public class SingleLineChart extends CustomChart {
    private final Callable<Integer> callable;

    public SingleLineChart(String chartId, Callable<Integer> callable) {
        super(chartId);
        this.callable = callable;
    }

    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        int value = (Integer)this.callable.call();
        return value == 0 ? null : (new JsonObjectBuilder()).appendField("value", value).build();
    }
}
