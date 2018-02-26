package input

import (
	"bufio"
	"log"

	"os"
	"strconv"
	"strings"

	logger "github.com/sirupsen/logrus"
)

type Cell struct {
	Tomato, Taken bool
}

type Inputs struct {
	NbRows, NbColumns, MinNumberOfIngredient, MaxSizeSlice int
	Cells                                                  [][]Cell
}

func GetInputs(path string) Inputs {
	file, err := os.Open(path)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	i := 0
	var datas Inputs

	for scanner.Scan() { //chaque ligne
		ligne := scanner.Text()
		if i == 0 {
			logger.Info(ligne)
			parameters := strings.Split(ligne, " ")
			if len(parameters) != 4 {
				log.Fatalf("Have %d parameters instead of 4 : for line %d", len(parameters), i)
			}

			datas.NbRows, err = strconv.Atoi(parameters[0])
			if err != nil {
				log.Fatalf("Cannot parse nbRows %v", parameters[0])
			}
			datas.NbColumns, err = strconv.Atoi(parameters[1])
			if err != nil {
				log.Fatalf("Cannot parse nbColumns %v", parameters[1])
			}
			datas.MinNumberOfIngredient, err = strconv.Atoi(parameters[2])
			if err != nil {
				log.Fatalf("Cannot parse minNumberOfIngredient %v", parameters[2])
			}
			datas.MaxSizeSlice, err = strconv.Atoi(parameters[3])
			if err != nil {
				log.Fatalf("Cannot parse maxSizeSlice %v", parameters[3])
			}

			datas.Cells = make([][]Cell, datas.NbRows)
			for y := 0; y < datas.NbRows; y++ {
				datas.Cells[y] = make([]Cell, datas.NbColumns)
			}
		} else {
			for nb, c := range ligne {
				datas.Cells[i-1][nb].Tomato = (c == 'T')
			}
		}
		i++
	}

	return datas
}
