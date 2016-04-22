var _clickedRow = null;
var _defaultColor = '#EFF7FF';
var _changedColor = '#DFEFFF';
var _defaultFontColor = 'black';
var _changedFontColor = '#FF6600';

function clickRow(row)
{
    if (!_clickedRow)
    {
        _clickedRow = row;
        row.style.backgroundColor = _changedColor;
        row.style.color=_changedFontColor;
    }
    else if (row != _clickedRow)
    {
        _clickedRow.style.backgroundColor = _defaultColor;
        _clickedRow.style.color = _defaultFontColor;
        row.style.backgroundColor = _changedColor;
        row.style.color=_changedFontColor;
        _clickedRow = row;
    }
}

function overRow(row)
{
    if (row != _clickedRow)
    {
        row.style.backgroundColor = _changedColor;
        row.style.color=_changedFontColor;
    }
}

function outRow(row)
{
    if (row != _clickedRow)
    {
        row.style.backgroundColor = _defaultColor;
       row.style.color = _defaultFontColor;
    }
}