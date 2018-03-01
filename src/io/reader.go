package io

import (
	"bufio"
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"os"
	"strconv"
	"strings"
)

func GetInputs(path string) {
	file, err := os.Open(path)
	if err != nil {
		logger.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	i := 0

	model.Rides = []*model.Ride{}
	for scanner.Scan() { //chaque ligne
		ligne := scanner.Text()
		parameters := strings.Split(ligne, " ")
		if i == 0 {
			logger.Info(ligne)
			if 6 != len(parameters) {
				logger.Error("First line has ", len(parameters), " instead of 6.")
			}

			model.GridRows, err = strconv.Atoi(parameters[0])
			if nil != err {
				logger.Error("Unable to parse grid rows", err)
			}
			model.GridColumns, err = strconv.Atoi(parameters[1])
			if nil != err {
				logger.Error("Unable to parse grid columns", err)
			}
			model.FleetSize, err = strconv.Atoi(parameters[2])
			if nil != err {
				logger.Error("Unable to parse fleet size", err)
			}
			model.NbRides, err = strconv.Atoi(parameters[3])
			if nil != err {
				logger.Error("Unable to parse number of rides", err)
			}
			model.BonusPerRide, err = strconv.Atoi(parameters[4])
			if nil != err {
				logger.Error("Unable to parse bonus per ride", err)
			}
			model.TotalTime, err = strconv.Atoi(parameters[5])
			if nil != err {
				logger.Error("Unable to parse nb of steps", err)
			}
		} else {
			ride := new(model.Ride)
			ride.BeginRow, err = strconv.Atoi(parameters[0])
			if nil != err {
				logger.Error("Unable to parse begin row for ride ", i)
			}
			ride.BeginColumn, err = strconv.Atoi(parameters[1])
			if nil != err {
				logger.Error("Unable to parse begin column for ride ", i)
			}
			ride.EndRow, err = strconv.Atoi(parameters[2])
			if nil != err {
				logger.Error("Unable to parse end row for ride ", i)
			}
			ride.EndColumn, err = strconv.Atoi(parameters[3])
			if nil != err {
				logger.Error("Unable to parse end column for ride ", i)
			}
			ride.EarlistStart, err = strconv.Atoi(parameters[4])
			if nil != err {
				logger.Error("Unable to parse earliest for ride ", i)
			}
			ride.LatestFinish, err = strconv.Atoi(parameters[5])
			if nil != err {
				logger.Error("Unable to parse latest finsh for ride ", i)
			}
			model.Rides = append(model.Rides, ride)
		}
		i++
	}
}
