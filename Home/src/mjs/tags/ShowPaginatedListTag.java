package mjs.tags;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.jsp.JspWriter;
import mjs.aggregation.OrderedMap;
import mjs.database.PaginatedList;
import mjs.database.Field;
import mjs.model.BusinessObject;
import mjs.utils.BeanUtils;
import mjs.utils.Constants;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShowPaginatedListTag extends AbstractTag {

    static final long serialVersionUID = -4174504602386548113L;

    /**
     * This is the list view style (list, details, thumbnails). Defaults to
     * "details".
     */
    private String view = "details";

    /**
     * This is the URL to use when generating the list. It should contain %s
     * where the primary key should be inserted.
     */
    private String url = null;

    /**
     * The class name of the form used for the filter mechanism when
     * view=details.
     */
    private String formName = null;

    /**
     * Constructor.
     */
    public ShowPaginatedListTag() {
        super();
    }

    /**
     * Execute.
     * 
     * @param tag
     * @return Description of Return Value
     */
    public int output(AbstractTag tag) {
        JspWriter out = tag.getWriter();

        try {
            if (view == null)
                view = "details";

            String varName = Constants.ATT_PAGINATED_LIST_CACHE;
            Object obj = req.getSession().getAttribute(varName);
            if (obj != null && obj instanceof PaginatedList) {
                log.debug("PaginatedList object found.");
                PaginatedList list = (PaginatedList) obj;

                if (list.getPageCount() > 1) {
                    out.println("          <table>");
                    out.println("            <tr>");
                    out.println("              <td width=\"20%\">&nbsp;</td>");
                    out.println("              <td width=\"60%\">Page: ");
                    out.println("                " + list.getCurrentPage() + " of "
                        + list.getPageCount());
                    out.println("                &nbsp;&nbsp;&nbsp;&nbsp;");
                    out.println("                &nbsp;&nbsp;&nbsp;&nbsp;");
                    out.println("                &nbsp;&nbsp;&nbsp;&nbsp;");
                    if (list.getCurrentPage() == 1) {
                        // Disabled.
                        out.println("                <img src=\"../images/vcr-first-disabled.jpg\" style=\"border: none\"></img>");
                        out.println("                <img src=\"../images/vcr-prev-disabled.jpg\" style=\"border: none\"></img>");
                    } else {
                        // Enabled.
                        out.println("                <a href=\"../VCRFirstPage.do\">");
                        out.println("                  <img src=\"../images/vcr-first.jpg\" style=\"border: none\"></img>");
                        out.println("                </a>");
                        out.println("                <a href=\"../VCRPrevPage.do\">");
                        out.println("                  <img src=\"../images/vcr-prev.jpg\" style=\"border: none\"></img>");
                        out.println("                </a>");
                    }
                    if (list.getCurrentPage() == list.getPageCount()) {
                        out.println("                <img src=\"../images/vcr-next-disabled.jpg\" style=\"border: none\"></img>");
                        out.println("                <img src=\"../images/vcr-last-disabled.jpg\" style=\"border: none\"></img>");
                    } else {
                        out.println("                <a href=\"../VCRNextPage.do\">");
                        out.println("                  <img src=\"../images/vcr-next.jpg\" style=\"border: none\"></img>");
                        out.println("                </a>");
                        out.println("                <a href=\"../VCRLastPage.do\">");
                        out.println("                  <img src=\"../images/vcr-last.jpg\" style=\"border: none\"></img>");
                        out.println("                </a>");
                    }
                    out.println("              </td>");
                    out.println("              <td width=\"20%\">&nbsp;</td>");
                    out.println("            </tr>");
                    out.println("          </table>");
                    out.println("<p/>&nbsp;");
                }

                out.println("<!--BEGIN SHOW_PAGINATED_LIST TAG-->");
                if (view.equalsIgnoreCase("list")) {

                    list.setPageLength(30);
                    int startOfPage = list.getStartOfPage();
                    int endOfPage = list.getEndOfPage();
                    log.debug("Page:  Start=" + startOfPage + " End=" + endOfPage);
                    ArrayList items = list.getItemsOnCurrentPage();

                    // Split into columns.
                    ArrayList column1 = new ArrayList();
                    ArrayList column2 = new ArrayList();
                    ArrayList column3 = new ArrayList();
                    for (int i = 0; i <= items.size() - 1; i++) {
                        Object obj2 = items.get(i);
                        if ((obj2 instanceof BusinessObject) || 
                            (obj2 instanceof Map)) {
                            if (i < 10)
                                column1.add(obj2);
                            else if (i < 20)
                                column2.add(obj2);
                            else if (i < 30)
                                column3.add(obj2);
                        } else {
                            log.error("Item #" + (i + 1) + ": unexpected object found.  Type="
                                + obj2.getClass().getName());
                        }
                    }

                    // Write to output.
                    Object item = null;
                    out.println("          <table width=\"100%\">");
                    for (int j = 0; j <= column1.size() - 1; j++) {
                        out.println("            <tr>");
                        out.println("              <td width=\"10%\">&nbsp;</td>");
                        
                        item = column1.get(j);
                        String name = get(item, "name");
                        String id = null;
                        String newUrl = null;
                        if (url != null) {
                            id = get(item, "pk");
                            newUrl = String.format(url, id);

                            log.debug("  id: " + id + "  url: " + url + "  newUrl: " + newUrl);
                        } else {
                            newUrl = "#";
                        }
                        out.println("              <td width=\"30%\"><li><a href=\"" + newUrl
                            + "\">" + name + "</a></li></td>");
                        if (j < column2.size()) {
                            item = column2.get(j);
                            name = get(item, "name");
                            if (url != null) {
                                id = get(item, "pk");
                                newUrl = String.format(url, id);
                            } else {
                                newUrl = "#";
                            }
                            out.println("              <td width=\"30%\"><li><a href=\"" + newUrl
                                + "\">" + name + "</a></li></td>");
                        } else {
                            out.println("              <td width=\"30%\">&nbsp;</td>");
                        }
                        if (j < column3.size()) {
                            item = column3.get(j);
                            name = get(item, "name");
                            if (url != null) {
                                id = get(item, "pk");
                                newUrl = String.format(url, id);
                            } else {
                                newUrl = "#";
                            }
                            out.println("              <td width=\"30%\"><li><a href=\"" + newUrl
                                + "\">" + name + "</a></li></td>");
                        } else {
                            out.println("              <td width=\"30%\">&nbsp;</td>");
                        }
                        out.println("            </tr>");
                    }
                    out.println("          </table>");
                } else if (view.equalsIgnoreCase("details")) {
                    log.debug("Creating details list.");
                    log.debug("Page count: " + list.getPageCount());
                    int startOfPage = list.getStartOfPage();
                    int endOfPage = list.getEndOfPage();
                    log.debug("Page:  Start=" + startOfPage + " End=" + endOfPage);
                    ArrayList items = list.getItemsOnCurrentPage();
                    if (items == null)
                        log.error("list.getItemsOnCurrentPage() is NULL.");
                    else
                        log.debug("list.getItemsOnCurrentPage().size() = " + items.size());

                    out.println("          <form name=\"" + formName
                        + "\" method=\"post\" action=\"" + list.getFilterAction() + "\">");

                    out.println("          <table width=\"100%\">");

                    // Header
                    out.println("            <tr>");
                    int counter = 0;
                    int ignoredColumns = 0;
                    OrderedMap defs = list.getFieldDefs();
                    Iterator iter = defs.iterator();
                    while (iter.hasNext()) {
                        Field nextField = (Field) iter.next();
                        if (nextField.getType() != null
                            && nextField.getType().equalsIgnoreCase("key")) {
                            // Ignore the key field.
                            ignoredColumns++;
                        } else {
                            StringBuilder builder = new StringBuilder();
                            builder.append("            ");
                            builder.append("<th align=\"left\"  ");

                            // IF NOT LAST ONE......
                            if (counter != (defs.size() - 1 - ignoredColumns)) {
                                builder.append("style=\"width: ");
                                builder.append(nextField.getListColumnWidth());
                                builder.append(";\"");
                            }

                            builder.append(">");
                            out.println(builder.toString());
                            out.println("              " + nextField.getCaption() + "&nbsp;");
                            out.println("            </th>");
                            counter++;
                        }
                    }
                    out.println("            </tr>");

                    // Filter
                    out.println("            <tr>");
                    counter = 0;
                    iter = defs.iterator();
                    while (iter.hasNext()) {
                        Field nextField = (Field) iter.next();
                        if (nextField.getType() != null
                            && nextField.getType().equalsIgnoreCase("key")) {
                            // Do nothing.
                        } else {
                            out.println("            <td>");
                            String value = "";
                            Map<String, String> searchCriteria = (Map<String, String>) tag
                                .getRequest().getSession()
                                .getAttribute(Constants.ATT_SEARCH_CRITERIA);
                            if (searchCriteria != null) {
                                value = searchCriteria.get(nextField.getName());
                                if (value == null) {
                                    value = "";
                                }
                            }

                            StringBuilder builder = new StringBuilder();
                            builder.append("              ");
                            builder.append("<input type=\"text\" name=\"");
                            builder.append(nextField.getName());
                            builder.append("\" maxlength=\"");
                            builder.append(nextField.getMaxLen());
                            builder.append("\" value=\"" + value + "\"");

                            // IF NOT LAST ONE......
                            if (counter != (defs.size() - 1 - ignoredColumns)) {
                                builder.append(" style=\"width: ");
                                builder.append(nextField.getListColumnWidth());
                                builder.append("; \"");
                            }
                            builder.append(">");
                            out.println(builder.toString());
                            if (counter == (defs.size() - 1 - ignoredColumns)) {
                                out.println("<input type=\"submit\" value=\"Filter\" alt=\"Execute filter\" />");
                            }
                            out.println("            </td>");
                            counter++;
                        }
                    }
                    out.println("            </tr>");
                    log.debug("Preparing to display " + items.size() + " items...");

                    for (int i = 0; i <= items.size() - 1; i++) {
                        Object obj2 = items.get(i);
                        iter = defs.iterator();
                        BusinessObject bizObj = null;
                        if (obj2 instanceof BusinessObject) {
                            // Write out next line.
                            if ((i + 1) % 2 == 0) {
                                out.println("            <tr class=\"highlight\">");
                            } else {
                                out.println("            <tr>");
                            }
                            bizObj = (BusinessObject) obj2;
                            Map<String, String> beanValues = BeanUtils.getBeanProperties(bizObj);
                            String newUrl = null;
                            if (url != null) {
                                id = bizObj.getPk();
                                newUrl = String.format(url, id);
                            } else {
                                newUrl = "#";
                            }
                            while (iter.hasNext()) {
                                Field nextField = (Field) iter.next();
                                if (nextField.getType() != null
                                    && nextField.getType().equalsIgnoreCase("key")) {
                                    // Do nothing.
                                } else {
                                    out.println("            <td>");
                                    // Get the value for this field.
                                    String fieldName = nextField.getName();
                                    if (fieldName.startsWith("@")) {
                                        fieldName = fieldName.substring(1);
                                    }
                                    String value = beanValues.get(fieldName);
                                    if (nextField.getIsLink()) {
                                        out.println("<a href=\"" + newUrl + "\">" + value + "</a>");
                                    } else {
                                        out.println(value);
                                    }
                                    out.println("            </td>");
                                }
                            }
                            out.println("            </tr>");
                        }
                    }
                    out.println("          </table>");
                    out.println("          </form>");

                } else {
                    out.println("Unsupported view type: " + view);
                }
            } else {
                log.debug("PaginatedList object not found.");
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

    /**
     * Get the specified property value from the specified bean.
     * 
     * @param bean Object
     * @param property String
     * @return String
     */
    public String get(Object bean,
                      String property) {
        try {
            if (bean instanceof Map) {
                Map map = (Map) bean;
                Object value = map.get(property);
                if (value != null) {
                    return value.toString();
                } else {
                    return "";
                }
            } else {
                PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());
                PropertyDescriptor pd = null;

                // Find the descriptor for this property.
                for (int i = 0; i <= pds.length - 1; i++) {
                    if (pds[i].getName().equals(property)) {
                        pd = pds[i];
                        break;
                    }
                }
                if (pd == null) {
                    throw new TagException("Unable to find property " + property + " in bean "
                        + bean.getClass().getName() + ".");
                }
                Method method = pd.getReadMethod();
                Object value = null;
                if (method != null) {
                    Object[] args = {};
                    value = method.invoke(bean, args);
                    if (value == null) {
                        return "";
                    } else {
                        return value.toString();
                    }
                } else {
                    throw new TagException("Unable to find read method for property " + property
                        + " in bean " + bean.getClass().getName() + ".");
                }
            }
        } catch (Exception e) {
            log.error("Failed to get '" + property + "' value from bean "
                + bean.getClass().getName() + ".", e);
            return "";
        }
    }

    /**
     * Returns the width of the filter edit box/picklist for a particular
     * 
     * @param actualFieldLength String
     * @return String
     */
    public String getFilterFieldWidth(Field field) {
        String startWidth = field.getListColumnWidth();
        String actualWidth = null;
        int offset = 0;
        if (startWidth.endsWith("%")) {
            return "98%";
        } else if (startWidth.endsWith("px")) {
            offset = 5;
            actualWidth = startWidth.substring(0, startWidth.length() - 2);
            int intValue = Integer.parseInt(actualWidth);
            return (intValue - offset) + "px";
        } else {
            return startWidth;
        }
    }

    /**
     * This is the list view style (list, details, thumbnails). Defaults to
     * "details".
     */
    public String getView() {
        return view;
    }

    /**
     * This is the list view style (list, details, thumbnails). Defaults to
     * "details".
     */
    public void setView(String value) {
        view = value;
    }

    /**
     * The class name of the form used for the filter mechanism when
     * view=details.
     */
    public String getFormName() {
        return formName;
    }

    /**
     * The class name of the form used for the filter mechanism when
     * view=details.
     */
    public void setFormName(String value) {
        formName = value;
    }

    /**
     * This is the URL to use when generating the list. It should contain %s
     * where the primary key should be inserted.
     */
    public String getUrl() {
        return url;
    }

    /**
     * This is the URL to use when generating the list. It should contain %s
     * where the primary key should be inserted.
     */
    public void setUrl(String value) {
        url = value;
    }

}
