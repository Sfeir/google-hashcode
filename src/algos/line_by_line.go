package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
)

func LineByLine(inputs *model.Inputs) []model.Slice {
	slices := []model.Slice{}

	newSlice := true
	var startColumn int
	nbSlices := 0

	for lineNumber, row := range inputs.Cells {
		logger.Debug(row)
		var nbTomato, nbMushroom, nbUnit int
		for colNumber, cell := range row {
			if newSlice {
				startColumn = colNumber
			}
			if cell.Tomato {
				nbTomato++
			} else {
				nbMushroom++
			}
			nbUnit++

			if nbTomato > inputs.MinNumberOfIngredient && nbMushroom > inputs.MinNumberOfIngredient {
				for i := startColumn; i < colNumber; i++ {
					inputs.Cells[lineNumber][i].Taken = true
				}
				logger.Debug("Slice : startColumn ", startColumn)
				logger.Debug("Slice : startRow ", lineNumber)
				logger.Debug("Slice : endColumn ", colNumber)
				logger.Debug("Slice : endRow ", lineNumber)
				slices = append(slices, model.Slice{startColumn, lineNumber, colNumber, lineNumber})
				nbSlices++
				newSlice = true
			} else if nbUnit > inputs.MaxSizeSlice {
				newSlice = true
			}
		}
	}

	logger.Info("Number of slices ", nbSlices)

	return slices[:nbSlices]
}
