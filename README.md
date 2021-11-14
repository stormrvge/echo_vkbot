# Echo VK Bot

### Requires:
* JDK 8

---
### How to run echo VK bot
#### 1. Clone repository
>`git clone https://github.com/stormrvge/echo_vkbot`
#### 2. Setup VK API and application properties
> 1. Open `src/main/resources folder`
> 2. Configure vk_api.properties file. (You must take them on the VK website)
#### 3. Build JAR file
> Run cmd/terminal, then go to root application folder and run `mvnw clean package`
#### 4. Run application
>1. Go to `target` folder and copy `echo_vkBot.jar` file to any empty folder. 
  This is needed for saving log file. <b> <br> If you don't move the jar file and rebuild application 
  your log file will be removed. </b>
>2. Run `java -jar echo_vkbot.jar` from root application folder.
----