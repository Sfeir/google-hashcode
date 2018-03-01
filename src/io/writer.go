package io

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	"strings"
	logger "github.com/sirupsen/logrus"
	"os"
	"bufio"
	"fmt"
)

func Write(inputPath string, res []model.Course) {
	outputPath := strings.Replace(inputPath, ".in", ".out", -1)
	outputPath = strings.Replace(outputPath, "input", "output", -1)
	logger.Info(outputPath)

	file, err := os.Create(outputPath)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	w := bufio.NewWriter(file)
	for i, c := range res {
		fmt.Fprintf(w, "%d ", c.Vehicule+1)
		for _, r := range c.Rides {
			fmt.Fprintf(w, "%d ", r)
		}
		if i != len(res)-1 {
			fmt.Fprintf(w, "\n")
		}
	}

	err = w.Flush()
	if err != nil {
		panic(err)
	}
}
