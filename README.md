# repl.utils [![Build Status](https://travis-ci.org/rksm/repl.utils.svg?branch=master)](https://travis-ci.org/rksm/repl.utils)

[![Clojars Project](http://clojars.org/rksm/repl.utils/latest-version.svg)](http://clojars.org/rksm/repl.utils)

just some helpers that I inject into my clojure runtime to improve development

## Usage

For example inject into `clojure.core` with [vinyasa](https://github.com/zcaudate/vinyasa) like

        (vinyasa.inject/in
         clojure.core
         [rksm.repl.utils lein search-for-symbol get-stack print-stack dumb-stack traced-fn])


## License

Copyright © 2014 Robert Krahn

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
