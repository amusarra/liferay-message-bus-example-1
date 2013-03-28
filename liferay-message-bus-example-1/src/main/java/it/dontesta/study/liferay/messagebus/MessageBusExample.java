/**
 * 
 */
package it.dontesta.study.liferay.messagebus;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.util.bridges.mvc.MVCPortlet;
import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;


/**
 * @author musarra
 *
 */
public class MessageBusExample extends MVCPortlet {

	public void sendNotificationEmail(ActionRequest actionRequest, 
		ActionResponse actionResponse)
		throws IOException, PortletException, SystemException {

		User user = (User) actionRequest.getAttribute(WebKeys.USER);
		
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setSubject("Test invio email via Message Bus");
		subscriptionSender.setBody("Ecco la mail via Message Bus");
		subscriptionSender.setUserId(user.getUserId());
		subscriptionSender.setCompanyId(user.getCompanyId());
		subscriptionSender.setFrom("noreply@liferay.com", "Liferay Portal");
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("user", user.getUserId());
		subscriptionSender.setServiceContext(new ServiceContext());

		subscriptionSender.addRuntimeSubscribers(
			user.getEmailAddress(),
			user.getFullName());

		List<EmailAddress> emails = (List<EmailAddress>) user.getEmailAddresses();
		if (emails.size() > 0) {
			if (_log.isInfoEnabled()) {
				_log.info("User " + user.getUserId() +
					" has additional emails address");
			}
			for (EmailAddress emailAddress : emails) {
				subscriptionSender.addRuntimeSubscribers(
					emailAddress.getAddress(),
					(String) user.getFullName());
				
			}
		}

		Message myMessage = new Message();
		myMessage.setDestinationName("liferay/subscription_sender");
		myMessage.setPayload(subscriptionSender);
		MessageBusUtil.sendMessage(myMessage.getDestinationName(),  myMessage);
	}
	
	private static Log _log =
		LogFactoryUtil.getLog(MessageBusExample.class);

}
