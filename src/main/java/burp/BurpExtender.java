package burp;

import java.awt.event.WindowAdapter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import assess.PostmanFrame;

public class BurpExtender implements IBurpExtender, IContextMenuFactory {
	private IExtensionHelpers helpers;
	private PrintWriter stderr;

	// Extension Name
	public static final String EXTENSION_NAME = "Postman Integration";

	// Extension Version
	public static final String VERSION_INFO = "2.0";
	
	// JFrame Title
	public static final String FRAME_TITLE = "Postman Integration";
	
	private IBurpExtenderCallbacks callbacks;
	
	private PostmanFrame frame;
	
	@Override
	public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
		// set extension name
		callbacks.setExtensionName(EXTENSION_NAME + " " + VERSION_INFO);
		callbacks.registerContextMenuFactory(this);

		// obtain error streams
		stderr = new PrintWriter(callbacks.getStderr(), true);
		
		// set helper
		this.helpers = callbacks.getHelpers();			
		
		// set callbacks
		this.callbacks = callbacks;
	}

	@Override
	/**
	 * IContextMenuFactory
	 */
	public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
		List<JMenuItem> menuList = new ArrayList<>();
		
		JMenuItem item = new JMenuItem("Add to Postman Collection");

		item.addActionListener(e -> {
			IHttpRequestResponse[] requestResponseArray = invocation.getSelectedMessages();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						List<IHttpRequestResponse> reqList = new ArrayList<>();

						// Generate Request List
						for (IHttpRequestResponse requestResponse : requestResponseArray) {
							reqList.add(requestResponse);
						}
						
						if (frame == null) {
							int posX = 20;	// View X position
							int posY = 20;	// View Y postion
							frame = new PostmanFrame(posX, posY, reqList.size());
							
							frame.addWindowListener(new WindowAdapter(){
								@Override
				                public void windowClosing(java.awt.event.WindowEvent e){
									frame = null;
				                }
							});
							
							frame.setRequest(reqList, BurpExtender.this.callbacks);
							// Set Title to Viewer
							frame.setTitle(FRAME_TITLE);
							
							// Show Viewer
							frame.setVisible(true);
						} else {
							frame.addRequest(reqList);
						}


					} catch (Exception e) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						pw.flush();
						String stackTrace = sw.toString();
						stderr.println(stackTrace);
						BurpExtender.this.callbacks.issueAlert("Some error happened. Please check Burp Extensions Errors tab. Message:" + e.getMessage());
					}
				}
			});
		});
		menuList.add(item);
		return menuList;
	}
}