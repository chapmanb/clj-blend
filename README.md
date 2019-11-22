# clj-blend

Clojure library for interacting with the Galaxy bioinformatics workflow platform.

## Usage

Requires Java 8 or better and [Leiningen 2.x][u1].

    $ lein repl
    > (require '[org.galaxyproject.clj-blend.galaxy.core :as galaxy])
    > (def c (galaxy/get-client "https://main.g2.bx.psu.edu/" "your-api-key"))
    > (def user (galaxy/get-user-info c))

[u1]: https://github.com/technomancy/leiningen

## License

The code is freely available under the [MIT license][l1].

[l1]: http://www.opensource.org/licenses/mit-license.html
