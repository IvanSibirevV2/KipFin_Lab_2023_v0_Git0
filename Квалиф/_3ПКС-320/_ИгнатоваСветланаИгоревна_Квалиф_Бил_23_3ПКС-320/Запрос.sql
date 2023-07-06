SELECT d.NameP, e.NameOb, SUM(s.Price*s.Quantity) AS Общая_Стоимость
FROM Purchase s
JOIN Devices e ON s.ID = e.ManufacturerID
JOIN Manufacturer d ON d.Id = e.ManufacturerID
GROUP BY d.NameP, e.NameOb;