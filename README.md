## ActiveMQ Authentication Plugin template
This repo is a template for developing your ActiveMQ authentication plugin.

Its main purpose is to define the bare minimum dependencies and steps to add it to your ActiveMQ installation.

This simple authentication plugin authenticates using username and password


### Step 1. Build the plugin jar

```shell
mvn package
```

### Step 2. Move the packaged jar to the activemq lib
- Download ActiveMQ 6.x [here](https://activemq.apache.org/components/classic/download/)
- Unzip the tarball or zip file you just downloaded. For example, `unzip ~/Downloads/apache-activemq-6.1.5-bin.zip`
- Copy the built jar in the `lib` sub-folder of the installation. For example`cp ./target/activemq-plugin-1.0-SNAPSHOT.jar ~/Downloads/apache-activemq-6.1.5/lib/`
- Add the plugin to activemq.xml
```
<plugins>
    <bean xmlns="http://www.springframework.org/schema/beans" id="activemqTestPlugin" class="com.activemq.auth.plugin.SampleAuthenticationPlugin">
      <users>
        <authenticationUser username="user" password="password" groups="users"/>
      </users>
    </bean>
</plugins>
```

### Step 3. Start the ActiveMQ broker
```shell
$ cd ~/Downloads/apache-activemq-6.1.5/
$ ./bin/activemq console 
```

### Step 4. Trying sending a message to the broker with the configured username and password
```shell
$ ./bin/activemq producer --brokerUrl "tcp://localhost:61616" --user $username --password $password --destination queue://tq --message 10 --messageSize 100
```