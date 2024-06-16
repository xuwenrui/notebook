是的，Java 11中一个显著的变化是正式移除了Java EE（Java Platform, Enterprise Edition）和CORBA（Common Object Request Broker Architecture）相关的模块。这些模块在Java SE 9中已经被标记为废弃（deprecated），并计划在未来的版本中删除，而Java 11正是执行这一计划的版本。

### Java EE模块移除

Java EE是一套为企业级应用提供的一系列服务和API的集合，包括Servlets、JPA（Java Persistence API）、EJB（Enterprise JavaBeans）等。随着Java EE的发展，它被重新命名为Jakarta EE，并转移到了Eclipse基金会进行管理。Java 11中移除这些模块反映了Oracle希望减少JDK的体积，同时鼓励开发者直接从相应的项目或供应商那里获取最新的Java EE技术实现。

### CORBA模块移除

CORBA是一种跨平台的分布式对象中间件标准，用于让不同的系统上的对象能够相互通信。尽管曾经广泛应用于企业系统集成，但随着时间的推移，其使用频率已经大大降低，被更现代的技术如Web Services、RESTful API等所取代。因此，Java 11中移除CORBA模块也是为了简化JDK并减轻维护负担。

### 影响与应对

对于依赖这些模块的开发者和应用，Java 11之后需要做相应的调整。如果仍需要使用Java EE或CORBA功能，可以从第三方库或供应商提供的实现中获取。例如，Java EE的API可以通过MicroProfile项目、Jakarta EE项目或者直接使用特定的库（如Hibernate for JPA）来获得支持。