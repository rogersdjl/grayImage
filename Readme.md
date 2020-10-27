# GrayImage
> GrayIamge can read the average intensity in a specific square of newly created figures dynamicly.  
> Code is based on Java8.

# Installation 
1. Open the terminal
> This program need Java8 enviroment.
2. cd root_file_path  
> Before you compile the code, make sure that all the setup is done:  
> * ```/com/djl/ImageResolve.java```  
>   * Line - 8 : ``` String pathname = "File_path"; ```  
> should be filled with proper file path which is > going to store the figures.
>   * Line - 11 : ```BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname, "cOordinates.txt"))));```  
> Square setting in 'cOordinates.txt'  
> leftup_X, Leftup_Y, Rightdown_X, Rightdown_Y
> * ```/com/djl/MyPanel.java```
>   * Line - 117 : ```File f = new File            ("intensityLog_path");```  
> determine the intensity log path.
3. Compile the all the file
> ```javac com/djl/*.java```
4. Run the program directly
> ```java com/djl/ImageResolve```
5. Packaging the program(**optional**)
> ```jar cvfm IntensityLog.jar MANIFEST.MF -C com/djl .```