import React, { useEffect, useState } from "react";
import {
  Container,
  Typography,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Grid,
  Box,
  Paper,
} from "@mui/material";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip,
  Legend
);

const App = () => {
  const [minutes, setMinutes] = useState(50);
  const [data, setData] = useState(null);
  const [tickers] = useState(["NVDA", "PYPL"]);

  const fetchData = async () => {
    try {
      const res = await fetch(
        `http://localhost:8080/stockcorrelation?minutes=${minutes}&ticker1=${tickers[0]}&ticker2=${tickers[1]}`
      );
      const result = await res.json();
      setData(result);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData();
  }, [minutes]);

  const formatChartData = (stock) => {
    if (!data?.stocks?.[stock]) return {};
    const prices = data.stocks[stock].priceHistory;
    const labels = prices.map((p) => new Date(p.lastUpdatedAt).toLocaleTimeString());
    const values = prices.map((p) => p.price);
    const avg = data.stocks[stock].averagePrice;
    return {
      labels,
      datasets: [
        {
          label: `${stock} Price`,
          data: values,
          fill: false,
          borderColor: "#3f51b5",
        },
        {
          label: `Average Price`,
          data: Array(values.length).fill(avg),
          fill: false,
          borderColor: "#f50057",
          borderDash: [5, 5],
        },
      ],
    };
  };

  return (
    <Container>
      <Typography variant="h4" align="center" gutterBottom>
        Stock Price Aggregation Dashboard
      </Typography>
      <FormControl fullWidth sx={{ marginBottom: 2 }}>
        <InputLabel id="minutes-label">Minutes</InputLabel>
        <Select
          labelId="minutes-label"
          value={minutes}
          label="Minutes"
          onChange={(e) => setMinutes(e.target.value)}
        >
          {[10, 20, 30, 50, 60].map((m) => (
            <MenuItem key={m} value={m}>
              {m} minutes
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <Grid container spacing={4}>
        {tickers.map((ticker) => (
          <Grid item xs={12} md={6} key={ticker}>
            <Paper sx={{ padding: 2 }} elevation={3}>
              <Typography variant="h6" gutterBottom>
                {ticker} Price Chart
              </Typography>
              <Line data={formatChartData(ticker)} />
            </Paper>
          </Grid>
        ))}
      </Grid>
      {data?.correlation !== undefined && (
        <Box mt={4} textAlign="center">
          <Typography variant="h6">
            Correlation Between {tickers[0]} & {tickers[1]}: {data.correlation.toFixed(4)}
          </Typography>
        </Box>
      )}
    </Container>
  );
};


export default App;