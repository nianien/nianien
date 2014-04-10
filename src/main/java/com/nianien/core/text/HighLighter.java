package com.nianien.core.text;

import com.nianien.core.comparator.StringComparator;
import com.nianien.core.text.TextAnalyzer.Fragment;
import com.nianien.core.text.TextAnalyzer.FragmentHandler;
import com.nianien.core.util.StringUtils;

import java.util.Arrays;

/**
 * 高亮显示工具类,支持多个关键词的高亮显示,如果多个关键字之间存在包含关系,优先高亮显示较长的关键字
 *
 * @author skyfalling
 */
public class HighLighter {

    /**
     * 高亮显示样式
     */
    private String cssStyle;
    /**
     * 高亮显示的关键字列表
     */
    private String[] keywords;
    /**
     * 高亮显示时是否忽略大小写
     */
    private boolean ignoreCase;

    /**
     * 高亮显示样式
     *
     * @return
     */
    public String getCssStyle() {
        return cssStyle;
    }

    /**
     * 设置高亮显示样式<br> 形如:
     * style="color:#FF0000;font-weight:bold;"或class="keywords"
     *
     * @param cssStyle
     */
    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    /**
     * 高亮显示时是否忽略大小写
     *
     * @return
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * 设置高亮显示时是否忽略大小写
     *
     * @param ignoreCase
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * 高亮显示的关键字列表
     *
     * @return
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * 设置高亮显示的关键字列表
     *
     * @param keywords
     */
    public void setKeywords(String[] keywords) {
        Arrays.sort(keywords, new StringComparator(
                StringComparator.SortType.LengthDesc));
        this.keywords = keywords;
    }

    /**
     * 构造函数
     *
     * @param keywords 高亮显示的关键字列表
     */
    public HighLighter(String[] keywords) {
        this(keywords, false);
    }

    /**
     * 构造函数
     *
     * @param keywords   高亮显示的关键字列表
     * @param ignoreCase 是否忽略大小写
     */
    public HighLighter(String[] keywords, boolean ignoreCase) {
        this(keywords, ignoreCase, "style=\"color:#FF0000;font-weight:bold;\"");
    }

    /**
     * 构造函数
     *
     * @param keywords   高亮显示的关键字列表
     * @param ignoreCase 是否忽略大小写
     * @param cssStyle   高亮显示样式
     */
    public HighLighter(String[] keywords, boolean ignoreCase, String cssStyle) {
        this.setKeywords(keywords);
        this.setIgnoreCase(ignoreCase);
        this.setCssStyle(cssStyle);
    }

    /**
     * 高亮显示HTML正文中的关键字
     *
     * @param html HTML文本
     * @return
     */
    public String highLightHtml(String html) {

        /**
         * 正则表达式分割HTML标签和正文内容,匹配正则式的为标签,不匹配的为正文
         */
        return TextAnalyzer.rebuild(html, TextAnalyzer.analyze(html, "<.*?>"),
                new FragmentHandler() {

                    @Override
                    public String handle(String html, Fragment fragment) {
                        String value = fragment.content(html);
                        return fragment.isMatched() ? value
                                : highLightText(value);
                    }
                });
    }

    /**
     * 高亮显示纯文本中的关键字
     *
     * @param text 文本内容
     * @return
     */
    public String highLightText(String text) {

        return TextAnalyzer.rebuild(text,
                TextAnalyzer.analyze(text, keywords, ignoreCase),
                new FragmentHandler() {

                    @Override
                    public String handle(String text, Fragment fragment) {
                        String value = fragment.content(text);
                        return fragment.isMatched() ? highLight(value) : value;
                    }
                });
    }

    /**
     * 高亮显示关键字,重写该方法以实现不同的高亮模式
     *
     * @param keyword
     * @return
     */
    protected String highLight(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("<span ").append(StringUtils.emptyInstead(cssStyle, "")).append(" >").append(keyword).append("</span>");
        return sb.toString();
    }

}
