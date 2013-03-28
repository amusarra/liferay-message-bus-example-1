<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />

<portlet:actionURL name="sendNotificationEmail" var="sendNotificationEmailActionURL">
	<portlet:param name="jspPage" value="/view.jsp" />
</portlet:actionURL>

This is the <b>Message Bus Example</b> portlet.

<aui:button-row>
	<aui:button value="Send Notification via Email (by Message Bus)" type="button"  onClick="<%=sendNotificationEmailActionURL %>"/>
</aui:button-row>
