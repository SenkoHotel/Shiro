FROM mcr.microsoft.com/dotnet/sdk:7.0-alpine AS build
COPY ./Shiro /src/bot
COPY ./HotelLib /src/HotelLib
WORKDIR /src/bot
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish Shiro -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM mcr.microsoft.com/dotnet/runtime:7.0-alpine AS final
WORKDIR /app
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "Shiro.dll"]
