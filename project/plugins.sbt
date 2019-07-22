// The Play plugin
resolvers += Resolver.url("bintray-sbt-plugins", url("https://dl.bintray.com/eed3si9n/sbt-plugins/com.eed3si9n/sbt-assembly/scala_2.12/sbt_1.0/0.14.7/ivys/:ivy.xml"))(Resolver.ivyStylePatterns)
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.7")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.13")

/*resolvers += Resolver.url("heroku-sbt-plugin-releases",
  url("https://dl.bintray.com/heroku/sbt-plugins/"))(Resolver.ivyStylePatterns)
  */
// Play enhancer - this automatically generates getters/setters for public fields
// and rewrites accessors of these fields to use the getters/setters. Remove this
// plugin if you prefer not to have this feature, or disable on a per project
// basis using disablePlugins(PlayEnhancer) in your build.sbt
addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.2.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.1.0")
//addSbtPlugin("com.heroku" % "sbt-heroku" % "0.1.5")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.7.6")



//addSbtPlugin("me.amanj" %% "sbt-deploy" % "2.3.3")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")

