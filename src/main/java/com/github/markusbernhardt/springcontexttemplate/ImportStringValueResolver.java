package com.github.markusbernhardt.springcontexttemplate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringValueResolver;

public class ImportStringValueResolver implements StringValueResolver {

	/**
	 * The regex pattern used later to match the variables to replace
	 */
	protected final Pattern pattern;

	/**
	 * The map storing all the mappings
	 */
	protected final Map<String, String> mappings;

	/**
	 * Constructor
	 * 
	 * @param map
	 *            the map containing all value to resolved value mappings
	 */
	public ImportStringValueResolver(Map<String, String> mappings) {
		super();
		this.pattern = getPattern(mappings);
		this.mappings = mappings;
	}

	/**
	 * Resolve the given String value, for example parsing placeholders.
	 * 
	 * @param value
	 *            the original String value
	 * @return the resolved String value
	 */
	@Override
	public String resolveStringValue(String value) {
		StringBuffer sb = new StringBuffer();
		Matcher matcher = pattern.matcher(value);
		while (matcher.find()) {
			matcher.appendReplacement(sb, mappings.get(matcher.group(1)));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Build the regex pattern used later to match the variables to replace
	 * 
	 * @param mappings
	 *            the mappingt to match
	 * @return the pattern
	 */
	protected Pattern getPattern(Map<String, String> mappings) {
		String join = "";
		StringBuilder patternString = new StringBuilder();
		patternString.append("\\$\\{(");
		for (String key : mappings.keySet()) {
			patternString.append(join);
			patternString.append(key);
		}
		patternString.append(")\\}");
		return Pattern.compile(patternString.toString());
	}

}
