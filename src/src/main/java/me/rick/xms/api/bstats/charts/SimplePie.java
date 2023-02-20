//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.rick.xms.api.bstats.charts;

import java.util.concurrent.Callable;
import me.rick.xms.api.bstats.json.JsonObjectBuilder;

public class SimplePie extends CustomChart {
    private final Callable<String> callable;

    public SimplePie(String chartId, Callable<String> callable) {
        super(chartId);
        this.callable = callable;
    }

    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        String value = (String)this.callable.call();
        return value != null && !value.isEmpty() ? (new JsonObjectBuilder()).appendField("value", value).build() : null;
    }
}
