/*
 * (C) Copyright 2013 Krzysztof Kosmatka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package pl.kosmatka.jetm.renderers.html;

import java.util.Collection;
import java.util.Map;

import pl.kosmatka.jetm.renderers.i18n.Messages;
import etm.core.aggregation.Aggregate;
import etm.core.renderer.MeasurementRenderer;

/**
 * Renderer for JETM measurements. Outputs an HTML tree-table using
 * http://ludo.cubicphuse.nl/jquery-treetable
 * 
 * @author Krzysztof Kosmatka
 * 
 */
public class JQueryTreetableRenderer implements MeasurementRenderer {

	private static final String TABLE_ID = "jetm_result"; //$NON-NLS-1$
	private String htmlTableContent = "<b>" + Messages.getString("JQueryTreetableRenderer.notRendededMessage") //$NON-NLS-1$ //$NON-NLS-2$
			+ "</b>"; //$NON-NLS-1$

	public void render(Map points) {
		Collection<Aggregate> aggregates = points.values();
		StringBuilder builder = new StringBuilder();
		builder.append("<table id='"); //$NON-NLS-1$
		builder.append(TABLE_ID);
		builder.append("'>\n"); //$NON-NLS-1$
		builder.append("<caption>"); //$NON-NLS-1$
		builder.append("<a href='#' onclick=\"jQuery('#"); //$NON-NLS-1$
		builder.append(TABLE_ID);
		builder.append("').treetable('expandAll'); return false;\">"); //$NON-NLS-1$
		builder.append(Messages.getString("JQueryTreetableRenderer.expandAll")); //$NON-NLS-1$
		builder.append("</a>"); //$NON-NLS-1$
		builder.append("<a href=\"#\" onclick=\"jQuery('#"); //$NON-NLS-1$
		builder.append(TABLE_ID);
		builder.append("').treetable('collapseAll'); return false;\">"); //$NON-NLS-1$
		builder.append(Messages
				.getString("JQueryTreetableRenderer.collapseAll")); //$NON-NLS-1$
		builder.append("</a>"); //$NON-NLS-1$
		builder.append("</caption>"); //$NON-NLS-1$

		builder.append("<thead>\n"); //$NON-NLS-1$
		builder.append("<tr>\n"); //$NON-NLS-1$
		builder.append("<th>"); //$NON-NLS-1$
		builder.append(Messages
				.getString("JQueryTreetableRenderer.measurementPoint")); //$NON-NLS-1$
		builder.append("</th>\n"); //$NON-NLS-1$
		builder.append("<th>#</th>\n"); //$NON-NLS-1$
		builder.append("<th>"); //$NON-NLS-1$
		builder.append(Messages.getString("JQueryTreetableRenderer.average")); //$NON-NLS-1$
		builder.append("</th>\n"); //$NON-NLS-1$
		builder.append("<th>"); //$NON-NLS-1$
		builder.append(Messages.getString("JQueryTreetableRenderer.min")); //$NON-NLS-1$
		builder.append("</th>\n"); //$NON-NLS-1$
		builder.append("<th>"); //$NON-NLS-1$
		builder.append(Messages.getString("JQueryTreetableRenderer.max")); //$NON-NLS-1$
		builder.append("</th>\n"); //$NON-NLS-1$
		builder.append("<th>"); //$NON-NLS-1$
		builder.append(Messages.getString("JQueryTreetableRenderer.total")); //$NON-NLS-1$
		builder.append("</th>\n"); //$NON-NLS-1$
		builder.append("</tr>\n"); //$NON-NLS-1$
		builder.append("</thead>\n"); //$NON-NLS-1$
		builder.append("<tbody>\n"); //$NON-NLS-1$
		int i = 1;
		for (Aggregate aggregate : aggregates) {
			appendRowsToStringBuilder(builder, aggregate, null,
					Integer.toString(i));
			i++;
		}
		builder.append("</tbody>\n"); //$NON-NLS-1$
		builder.append("</table>\n"); //$NON-NLS-1$
		htmlTableContent = builder.toString();

	}

	public void appendRowsToStringBuilder(StringBuilder builder,
			Aggregate aggregate, String parentRowId, String rowId) {
		builder.append("<tr data-tt-id='"); //$NON-NLS-1$
		builder.append(rowId);
		builder.append("'"); //$NON-NLS-1$
		if (parentRowId != null) {
			builder.append(" data-tt-parent-id='"); //$NON-NLS-1$
			builder.append(parentRowId);
			builder.append("'"); //$NON-NLS-1$
		}
		builder.append("><td>"); //$NON-NLS-1$
		String pattern = "%,.3f"; //$NON-NLS-1$
		builder.append(aggregate.getName());
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("<td>"); //$NON-NLS-1$
		builder.append(aggregate.getMeasurements());
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("<td>"); //$NON-NLS-1$
		builder.append(String.format(pattern, aggregate.getAverage()));
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("<td>"); //$NON-NLS-1$
		builder.append(String.format(pattern, aggregate.getMin()));
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("<td>"); //$NON-NLS-1$
		builder.append(String.format(pattern, aggregate.getMax()));
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("<td>"); //$NON-NLS-1$
		builder.append(String.format(pattern, aggregate.getTotal()));
		builder.append("</td>"); //$NON-NLS-1$
		builder.append("</tr>\n"); //$NON-NLS-1$
		int i = 1;
		for (Object child : aggregate.getChilds().values()) {
			appendRowsToStringBuilder(builder, (Aggregate) child, rowId, rowId
					+ "-" + i); //$NON-NLS-1$
			i++;
		}
	}

	public String getHtmlDocument() {
		StringBuilder builder = new StringBuilder();
		builder.append("<!DOCTYPE html>\n"); //$NON-NLS-1$
		builder.append("\n"); //$NON-NLS-1$
		builder.append("<html>\n"); //$NON-NLS-1$
		builder.append("<head>\n"); //$NON-NLS-1$
		builder.append("<meta charset='utf-8'>\n"); //$NON-NLS-1$
		builder.append("<link rel='stylesheet' href='http://ludo.cubicphuse.nl/jquery-treetable/stylesheets/jquery.treetable.css' />\n"); //$NON-NLS-1$
		builder.append("<link rel='stylesheet' href='http://ludo.cubicphuse.nl/jquery-treetable/stylesheets/jquery.treetable.theme.default.css' />\n"); //$NON-NLS-1$
		builder.append("<title>JETM</title>\n"); //$NON-NLS-1$
		builder.append("</head>\n"); //$NON-NLS-1$
		builder.append("<body>\n"); //$NON-NLS-1$

		builder.append(htmlTableContent);

		builder.append("<script src='http://code.jquery.com/jquery-1.10.2.min.js'></script>\n"); //$NON-NLS-1$
		builder.append("<script src='http://code.jquery.com/ui/1.10.3/jquery-ui.min.js'></script>\n"); //$NON-NLS-1$
		builder.append("<script src='http://ludo.cubicphuse.nl/jquery-treetable/javascripts/src/jquery.treetable.js'></script>\n"); //$NON-NLS-1$
		builder.append("<script>"); //$NON-NLS-1$
		builder.append("$('#"); //$NON-NLS-1$
		builder.append(TABLE_ID);
		builder.append("').treetable({expandable: true});\n"); //$NON-NLS-1$
		builder.append("$('#"); //$NON-NLS-1$
		builder.append(TABLE_ID);
		builder.append(" tbody').on('mousedown', 'tr', function() {\n"); //$NON-NLS-1$
		builder.append("$('.selected').not(this).removeClass('selected');\n"); //$NON-NLS-1$
		builder.append("$(this).toggleClass('selected');\n"); //$NON-NLS-1$
		builder.append("});"); //$NON-NLS-1$
		builder.append("</script>\n"); //$NON-NLS-1$
		builder.append("</body>\n"); //$NON-NLS-1$
		builder.append("</html>\n"); //$NON-NLS-1$

		return builder.toString();

	}

}
