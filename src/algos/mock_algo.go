package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
)

func MockAlgo(inputs *model.Inputs) []model.Slice {
	for _, cell := range inputs.Cells {
		logger.Debug(cell)
	}
	return []model.Slice{}
}
