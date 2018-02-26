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
		colNumber := 0
		for colNumber < len(row) {
			cell := row[colNumber]
			if newSlice {
				nbTomato = 0
				nbMushroom = 0
				startColumn = colNumber
				newSlice = false
			}
			if cell.Tomato {
				nbTomato++
			} else {
				nbMushroom++
			}
			nbUnit++

			if nbUnit > inputs.MaxSizeSlice {
				colNumber = startColumn
				newSlice = true
			} else if nbTomato > inputs.MinNumberOfIngredient && nbMushroom > inputs.MinNumberOfIngredient {
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
			}
			colNumber++
		}
	}

	logger.Info("Number of slices ", nbSlices)

	return slices
}
