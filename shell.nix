with import <nixpkgs> {};
with pkgs;

let
  jdk = jdk12_headless;
  sbt = pkgs.sbt.overrideDerivation (
    _: rec {
      version = "1.3.7";

      src = fetchurl {
        urls = [
          "https://piccolo.link/sbt-${version}.tgz"
          "https://github.com/sbt/sbt/releases/download/v${version}/sbt-${version}.tgz"
        ];
        sha256 = "813d4a3b7d2f9d8e5585d959fd5bc389c999770d5b6f2b9c313cc009f7729814";
      };

      buildInputs = [ makeWrapper ];

      installPhase = ''
        mkdir -p $out/share/sbt $out/bin
        cp -ra . $out/share/sbt
        ln -s $out/share/sbt/bin/sbt $out/bin/
        wrapProgram $out/bin/sbt \
          --prefix PATH : ${lib.makeBinPath [ jdk ]} \
          --set JAVA_HOME ${jdk}
      '';

      patchPhase = ''
        echo -java-home ${jdk.home} >> conf/sbtopts
      '';
    }
  );
in mkShell {
  name = "lib-env";
  nativeBuildInputs = [ sbt jdk ];
  shellHook = ''
    export JAVA_HOME=${jdk.home}
    export SBT_OPTS="-Xmx4g -Xms4g -Xss16M -XX:MaxMetaspaceSize=4G -server"
  '';
}
