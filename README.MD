<p align="center">
  <img src="images/screen_image.png" alt="postman-integration"/>
</p>

# Postman Integration
Postman Integration is an extension to generate **Postman Collection fomat json file**. This fork modifies the extension to permit you to add requests to the extension one at a time or in chunks. You no longer have to highlight all requests in one go. This allows you to, for example, right click on each repeater tab individually to add your requests to a single collection.

# Usage

* It's very easy. You can open Postman Integration window from context menu "Export as Postman Collection" on history tab. Multiple selection is OK!. Then  set up the followings on the window.

  * **Encoding** - This is encoding.
  
  * **Collection Name**: - Collection name which you want to name in Postman.

  * **Folder Name**: - Folder name which you want to name in Postman. You may use "Set ALL" button to set folder name to the field "Folder" for all of requests.

  * **Name**: - Request name which you want to name in Postman.

* Once you press **Export** button, this generate Postman Collection format json file. **You can import it into Postman**.

## License
MIT
