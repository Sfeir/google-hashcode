package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
)

func MockAlgo(inputs *model.Inputs) []model.Slice {
	for _, row := range inputs.Cells {
		for _, cell := range row {
			logger.Debug(cell)
		}
	}
	return []model.Slice{}
}
