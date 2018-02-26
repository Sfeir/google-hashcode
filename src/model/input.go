package model

type Inputs struct {
	NbRows, NbColumns, MinNumberOfIngredient, MaxSizeSlice int
	Cells                                                  [][]Cell
}
