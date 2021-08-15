**Setup and Run**

Download or clone FlickrTest and import in the Android Studio and run to device.

 1. Search any images and all images will be displayed in a grid with 3 columns.

**About the Test**

I have written a basic structure of MVVM. In this example, I've created a basic core framework (can be extended based on requirement.), Network Layer and one custom component for pagination. 

**Core Framework**

  * DataActivity 
  * DataView
  * DataViewModel
  
**Network**

   * ApiRepository
   * Factory
   * Exception and service interface
   
   In the Network layer, I have tried to cover most of all negative error scenarios and exceptions as per below.
   
   * Handle IOException as Networkexception and others as a GenericException
   * For the specific errors I have created RestException which has status, code and message. 
      I have displayed error messages whatever I am receiving from the backend api because I don't have customer friendly messages mapping with these error messages. 
  
**Layout and dispaly images**

 I've used picasso to handle image lazy loading and have created custom recyclerview to handle pagination.
 
 
**Test cases**

I've written quickly two test cases for the viewmodel. 
 
**Improvements:**

 I have displayed same loading for the search and pagination which could be separated.
 If I would have a mapping for error messages then I would have covered more error scenarios rather than displaying backend error messages.
 API base url and api_key should be move into .properties file
