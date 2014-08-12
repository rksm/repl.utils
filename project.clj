(defproject rksm/repl.utils "0.1.1-SNAPSHOT"
  :description "just some helpers that I inject into my clojure runtime to improve development"
  :url "https://github.com/rksm/repl.utils"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [io.aviso/pretty "0.1.8"]
                 [org.clojure/tools.trace "0.7.8"]
                 [leiningen #=(leiningen.core.main/leiningen-version)]]

  :scm {:url "git@github.com:rksm/repl.utils"}
  :pom-addition [:developers [:developer
                              [:name "Robert Krahn"]
                              [:url "http://github.com/rksm"]
                              [:email "robert.krahn@gmail.com"]
                              [:timezone "-9"]]])
