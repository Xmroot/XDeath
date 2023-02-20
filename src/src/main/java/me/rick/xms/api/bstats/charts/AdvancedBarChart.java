//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.rick.xms.api.bstats.charts;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import me.rick.xms.api.bstats.json.JsonObjectBuilder;

public class AdvancedBarChart extends CustomChart {
    private final Callable<Map<String, int[]>> callable;

    public AdvancedBarChart(String chartId, Callable<Map<String, int[]>> callable) {
        super(chartId);
        this.callable = callable;
    }

    protected JsonObjectBuilder.JsonObject getChartData() throws Exception {
        JsonObjectBuilder valuesBuilder = new JsonObjectBuilder();
        Map<String, int[]> map = (Map)this.callable.call();
        if (map != null && !map.isEmpty()) {
            boolean allSkipped = true;
            Iterator var4 = map.entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry<String, int[]> entry = (Map.Entry)var4.next();
                if (((int[])entry.getValue()).length != 0) {
                    allSkipped = false;
                    valuesBuilder.appendField((String)entry.getKey(), (int[])entry.getValue());
                }
            }

            if (allSkipped) {
                return null;
            } else {
                return (new JsonObjectBuilder()).appendField("values", valuesBuilder.build()).build();
            }
        } else {
            return null;
        }
    }
}
