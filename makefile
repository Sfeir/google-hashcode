BASEDIR=${PWD}

default: help

help:
	@echo "usage:"
	@echo "\t clean"
	@echo "\t build"
	@echo "\t run"

clean: clean-only dep build

clean-only:
	@rm -rf vendor

dep:
	@dep ensure

build:
	@go fmt github.com/Sfeir/google-hashcode-lille/...
	@go install github.com/Sfeir/google-hashcode-lille

run: build
	@google-hashcode-lille -d ${BASEDIR}

test: build
	@google-hashcode-lille -d ${BASEDIR} -t

debug: build
	@google-hashcode-lille -d ${BASEDIR} -t -v
