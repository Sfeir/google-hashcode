package main

import (
	"github.com/Sfeir/google-hashcode-lille/src/input"
	logger "github.com/sirupsen/logrus"
	"github.com/urfave/cli"
	"io/ioutil"
	"os"
)

func main() {

	cli.VersionFlag = cli.BoolFlag{
		Name:  "version, V",
		Usage: "print only the version",
	}

	app := cli.NewApp()
	app.Name = "hashcode-lille-2017"

	app.Flags = []cli.Flag{
		cli.StringFlag{
			Name:  "dir, d",
			Usage: "path of the working dir",
		},
		cli.BoolFlag{
			Name: "test, t",
		},
		cli.BoolFlag{
			Name: "verbose, v",
		},
	}

	app.Action = func(c *cli.Context) error {

		isTest := c.Bool("test")
		if isTest {
			logger.SetLevel(logger.InfoLevel)
			logger.Debug("verbose log activated")
		}

		if c.Bool("verbose") {
			logger.SetLevel(logger.DebugLevel)
			logger.Debug("verbose log activated")
		}

		workDir := c.String("dir")
		inputDir := workDir + string(os.PathSeparator) + "input"
		outputDir := workDir + string(os.PathSeparator) + "output"
		logger.Info("from: ", inputDir)
		logger.Info("to: ", outputDir)

		files, err := ioutil.ReadDir(inputDir)
		if err != nil {
			panic(err)
		}

		for _, file := range files {
			if file.Name() != ".DS_Store" {
				runOn(inputDir + string(os.PathSeparator) + file.Name())
			}
		}

		return nil
	}
	runErr := app.Run(os.Args)
	if runErr != nil {
		panic(runErr)
	}
}

func runOn(path string) {
	logger.Info("starting process for: ", path)
	data := input.GetInputs(path)

	logger.Info("NbRow : ", data.NbRows)
	logger.Info("NbColumns : ", data.NbColumns)
	logger.Info("MinNumberOfIngredient : ", data.MinNumberOfIngredient)
	logger.Info("MaxSizeSlice : ", data.MaxSizeSlice)
}
