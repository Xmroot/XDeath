//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.rick.xms.api.bstats.charts;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import me.rick.xms.api.bstats.json.JsonObjectBuilder;

public class SimpleBarChart extends CustomChart {
    private final Callable<Map<String, Integer>> callable;

    public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
        super(chartId);
        this.callable = callable;
    }

    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
        Map<String, Integer> map = (Map)this.callable.call();
        if (map != null && !map.isEmpty()) {
            Iterator var3 = map.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, Integer> entry = (Map.Entry)var3.next();
                valuesBuilder.appendField((String)entry.getKey(), new int[]{(Integer)entry.getValue()});
            }

            return (new JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
        } else {
            return null;
        }
    }
}
