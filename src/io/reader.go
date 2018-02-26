package io

import (
	"bufio"
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"os"
	"strconv"
	"strings"
)

func GetInputs(path string) model.Inputs {
	file, err := os.Open(path)
	if err != nil {
		logger.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	i := 0
	var datas model.Inputs

	for scanner.Scan() { //chaque ligne
		ligne := scanner.Text()
		if i == 0 {
			logger.Info(ligne)
			parameters := strings.Split(ligne, " ")
			if len(parameters) != 4 {
				logger.Fatalf("Have %d parameters instead of 4 : for line %d", len(parameters), i)
			}

			model.NbRows, err = strconv.Atoi(parameters[0])
			if err != nil {
				logger.Fatalf("Cannot parse nbRows %v", parameters[0])
			}
			model.NbColumns, err = strconv.Atoi(parameters[1])
			if err != nil {
				logger.Fatalf("Cannot parse nbColumns %v", parameters[1])
			}
			datas.MinNumberOfIngredient, err = strconv.Atoi(parameters[2])
			if err != nil {
				logger.Fatalf("Cannot parse minNumberOfIngredient %v", parameters[2])
			}
			datas.MaxSizeSlice, err = strconv.Atoi(parameters[3])
			if err != nil {
				logger.Fatalf("Cannot parse maxSizeSlice %v", parameters[3])
			}

			datas.Cells = make([][]model.Cell, model.NbRows)
			for y := 0; y < model.NbRows; y++ {
				datas.Cells[y] = make([]model.Cell, model.NbColumns)
			}
		} else {
			for nb, c := range ligne {
				datas.Cells[i-1][nb].Tomato = (c == 'T')
				datas.Cells[i-1][nb].Taken = false
			}
		}
		i++
	}

	return datas
}