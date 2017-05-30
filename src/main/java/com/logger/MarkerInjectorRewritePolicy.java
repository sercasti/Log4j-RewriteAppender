package com.logger;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.util.StringMap;

@Plugin(name = "InjectMarkerPolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class MarkerInjectorRewritePolicy implements RewritePolicy {

	@Override
	public LogEvent rewrite(final LogEvent event) {
		Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder();
		if (event.getMarker() != null) {
			StringMap contextData = ContextDataFactory.createContextData();
			contextData.putValue("_marker", event.getMarker().getName());
			builder.setContextData(contextData);
		}
		builder.setLoggerName(event.getLoggerName());
		builder.setMarker(event.getMarker());
		builder.setLoggerFqcn(event.getLoggerFqcn());
		builder.setLevel(event.getLevel());
		builder.setMessage(event.getMessage());
		builder.setThrown(event.getThrown());
		builder.setContextStack(event.getContextStack());
		builder.setThreadName(event.getThreadName());
		builder.setSource(event.getSource());
		builder.setTimeMillis(event.getTimeMillis());
		return builder.build();
	}

	@PluginFactory
	public static MarkerInjectorRewritePolicy createPolicy() {
		return new MarkerInjectorRewritePolicy();
	}
}
