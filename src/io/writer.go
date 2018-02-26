package io

import (
	"bufio"
	"fmt"
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
	"os"
	"strings"
)

func Write(inputPath string, res []model.Slice) {
	outputPath := strings.Replace(inputPath, ".in", ".out", -1)
	outputPath = strings.Replace(outputPath, "input", "output", -1)
	logger.Info(outputPath)

	file, err := os.Create(outputPath)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	w := bufio.NewWriter(file)

	fmt.Fprintln(w, len(res))

	for _, line := range res {
		fmt.Fprintln(w, line.ToStr())
	}
	err = w.Flush()
	if err != nil {
		panic(err)
	}
}
