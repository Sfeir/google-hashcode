package main

import (
	"github.com/Sfeir/google-hashcode-lille/src/io"
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"github.com/urfave/cli"
	"io/ioutil"
	"os"
	"github.com/Sfeir/google-hashcode-lille/src/algos"
)

//const testFile="a_example.in"
//const testFile ="b_should_be_easy.in"
//const testFile="c_no_hurry.in"
//const testFile="d_metropolis.in"
const testFile="e_high_bonus.in"


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
				if !isTest || file.Name() == testFile {
					runOn(inputDir + string(os.PathSeparator) + file.Name())
				}
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
	io.GetInputs(path)
	logger.Info("Grid Rows : ", model.GridRows)
	logger.Info("Grid Columns : ", model.GridColumns)
	logger.Info("Fleet size : ", model.FleetSize)
	logger.Info("Nb rides : ", model.NbRides)
	logger.Info("Bonus per Rides : ", model.BonusPerRide)
	logger.Info("Total time : ", model.TotalTime)


	res := algos.MockAlgo()
	io.Write(path, res)
}
