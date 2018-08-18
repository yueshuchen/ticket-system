package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import algorithm.GeoRecommendation;
import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;

/**
 * Servlet implementation class RecommendItem
 */
@WebServlet("/recommendation")
public class RecommendItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		GeoRecommendation recommendation = new GeoRecommendation();
		List<Item> items = recommendation.recommendItems(userId, lat, lon);

		JSONArray result = new JSONArray();
		try {
			for (Item item : items) {
				result.put(item.toJSONObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		RpcHelper.writeJsonArray(response, result); 


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
	   	 try {
	   		 JSONObject input = RpcHelper.readJSONObject(request);
	   		 String userId = input.getString("user_id");
	   		 JSONArray array = input.getJSONArray("favorite");
	   		 List<String> itemIds = new ArrayList<>();
	   		 for(int i = 0; i < array.length(); ++i) {
	   			 itemIds.add(array.getString(i));
	   		 }
	   		 connection.unsetFavoriteItems(userId, itemIds);
	   		 RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	   		 
	   	 } catch (Exception e) {
	   		 e.printStackTrace();
	   	 } finally {
	   		 connection.close();
	   	 }

	}

}
