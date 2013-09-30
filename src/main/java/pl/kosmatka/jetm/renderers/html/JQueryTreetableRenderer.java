package pl.kosmatka.jetm.renderers.html;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Map;

import etm.core.aggregation.Aggregate;
import etm.core.renderer.MeasurementRenderer;

public class JQueryTreetableRenderer implements MeasurementRenderer {

	private String htmlTableContent = "<b>Measurement not rendered</b>";

	public void render(Map points) {
		Collection<Aggregate> aggregates = points.values();
		StringBuilder builder = new StringBuilder();
		builder.append("<table id='jetm_result'>\n");
		builder.append("<caption>");
		builder.append("<a href='#' onclick=\"jQuery('#jetm_result').treetable('expandAll'); return false;\">Expand all</a>");
		builder.append("<a href=\"#\" onclick=\"jQuery('#jetm_result').treetable('collapseAll'); return false;\">Collapse all</a>");
		builder.append("</caption>");

		builder.append("<thead>\n");
		builder.append("<tr>\n");
		builder.append("<th>Measurement Point</th>\n");
		builder.append("<th>#</th>\n");
		builder.append("<th>Average</th>\n");
		builder.append("<th>Min</th>\n");
		builder.append("<th>Max</th>\n");
		builder.append("<th>Total</th>\n");
		builder.append("</tr>\n");
		builder.append("</thead>\n");
		builder.append("<tbody>\n");
		int i = 1;
		for (Aggregate aggregate : aggregates) {
			appendRowsToStringBuilder(builder, aggregate, null,
					Integer.toString(i));
			i++;
		}
		builder.append("</tbody>\n");
		builder.append("</table>\n");
		htmlTableContent = builder.toString();

	}

	public void appendRowsToStringBuilder(StringBuilder builder,
			Aggregate aggregate, String parentRowId, String rowId) {
		builder.append("<tr data-tt-id='");
		builder.append(rowId);
		builder.append("'");
		if (parentRowId != null) {
			builder.append(" data-tt-parent-id='");
			builder.append(parentRowId);
			builder.append("'");
		}
		builder.append("><td>");
		String pattern = "%,.3f";
		builder.append(aggregate.getName());
		builder.append("</td>");
		builder.append("<td>");
		builder.append(aggregate.getMeasurements());
		builder.append("</td>");
		builder.append("<td>");
		builder.append(String.format(pattern, aggregate.getAverage()));
		builder.append("</td>");
		builder.append("<td>");
		builder.append(String.format(pattern, aggregate.getMin()));
		builder.append("</td>");
		builder.append("<td>");
		builder.append(String.format(pattern, aggregate.getMax()));
		builder.append("</td>");
		builder.append("<td>");
		builder.append(String.format(pattern, aggregate.getTotal()));
		builder.append("</td>");
		builder.append("</tr>\n");
		int i = 1;
		for (Object child : aggregate.getChilds().values()) {
			appendRowsToStringBuilder(builder, (Aggregate) child, rowId, rowId
					+ "-" + i);
			i++;
		}
	}

	public String getHtmlDocument() {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>\n");
		builder.append("\n");
		builder.append("<html>\n");
		builder.append("<head>\n");
		builder.append("<meta charset='utf-8'>\n");
		builder.append("<link rel='stylesheet' href='http://ludo.cubicphuse.nl/jquery-treetable/stylesheets/jquery.treetable.css' />\n");
		builder.append("<link rel='stylesheet' href='http://ludo.cubicphuse.nl/jquery-treetable/stylesheets/jquery.treetable.theme.default.css' />\n");
		builder.append("<title>JETM</title>\n");
		builder.append("</head>\n");
		builder.append("<body>\n");

		builder.append(htmlTableContent);

		builder.append("<script src='http://code.jquery.com/jquery-1.10.2.min.js'></script>\n");
		builder.append("<script src='http://code.jquery.com/ui/1.10.3/jquery-ui.min.js'></script>\n");
		builder.append("<script src='http://ludo.cubicphuse.nl/jquery-treetable/javascripts/src/jquery.treetable.js'></script>\n");
		builder.append("<script>");
		builder.append("$('#jetm_result').treetable({expandable: true});\n");
		builder.append("$('#jetm_result tbody').on('mousedown', 'tr', function() {\n");
		builder.append("$('.selected').not(this).removeClass('selected');\n");
		builder.append("$(this).toggleClass('selected');\n");
		builder.append("});");
		builder.append("</script>\n");
		builder.append("</body>\n");
		builder.append("</html>\n");

		return builder.toString();

	}

}
