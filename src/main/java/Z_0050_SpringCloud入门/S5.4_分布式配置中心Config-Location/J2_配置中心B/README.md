访问规则: 

* /{application}/{profile}[/{label}]
* /{application}-{profile}.yml
* /{label}/{application}-{profile}.yml
* /{application}-{profile}.properties
* /{label}/{application}-{profile}.properties

实例：
* http://localhost:15111/config-client/dev/master
* http://localhost:15111/config-client-dev.yml
* http://localhost:15111/dev/config-client.yml
* http://localhost:15111/config-client-dev.properties
* http://localhost:15111/dev/config-client.properties

上面的url会映射{application}-{profile}.properties对应的配置文件，其中{label}对应Git上不同的分支，默认为master。我们可以尝试构造不同的url来访问不同的配置内容，比如，要访问master分支，config-client应用的dev环境，就可以访问这个url：http://localhost:1201/config-client/dev/master，并获得如下返回：
```json
{
    "name": "config-client",
    "profiles": [
        "dev"
    ],
    "label": "master",
    "version": null,
    "state": null,
    "propertySources": [
        {
            "name": "http://git.oschina.net/didispace/config-repo-demo/config-client-dev.yml",
            "source": {
                "info.profile": "dev"
            }
        },
        {
            "name": "http://git.oschina.net/didispace/config-repo-demo/config-client.yml",
            "source": {
                "info.profile": "default"
            }
        }
    ]
}
```

我们可以看到该Json中返回了应用名：config-client，环境名：dev，分支名：master，以及default环境和dev环境的配置内容。